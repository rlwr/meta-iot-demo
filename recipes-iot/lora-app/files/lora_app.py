#! /usr/bin/python

#    This program is free software: you can redistribute it and/or modify
#    it under the terms of the GNU General Public License as published by
#    the Free Software Foundation, either version 2 of the License, or
#    (at your option) any later version.

#    This program is distributed in the hope that it will be useful,
#    but WITHOUT ANY WARRANTY; without even the implied warranty of
#    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
#    GNU General Public License for more details.

#    You should have received a copy of the GNU General Public License
#    along with this program.  If not, see <http://www.gnu.org/licenses/>

import subprocess
import paho.mqtt.publish
import json

# Defines how to detect if a packet is a data packet
PKT_DATA = "\"AA555A0000BBDDAA\""

def parse_payload(str_in):
    str_out = ""
    
    # Example
    # "02070028-3020302E-3020302E-3020302E-3020302E-30203020-302E3020-302E3020-302E3020-44313900"
    # <USELESS>-<                                    DATA                             >-<USELESS>
    
    # Split with "-"
    st = str_in.split("-")
    
    for s in st:
        # Convert from hex string to ASCII
        # http://stackoverflow.com/questions/10618586/hex-string-to-character-in-python
        str_out = str_out +  ''.join(chr(int(s[i:i+2], 16)) for i in range(0, len(s), 2))

    return str_out.rstrip()

def lora_parse(str_in):
    lora_dump = []
    
    for line in str_in.splitlines():
        line = line.split(",")
        if not line:
            continue
        
        s = {}
        # Note: [1:-1] to remove leading and trailing quotes
        s['MAC']        = line[0][1:-1]                 # Gateway ID
        s['timestamp']  = line[2][1:-1]                 # UTC timestamp
        s['status']     = line[7][1:-2]                 # Status
        s['RSSI']       = line[13]                      # RSSI
        try:
            s['payload']  = json.loads(parse_payload(line[15][1:-1])) # Payload (as JSON)

            # TODO: filter elsewhere
            if s['status'] == "CRC_OK":
                lora_dump.append(s)

        except ValueError:
            s['payload']  = ""

    return lora_dump

# Send MQTT command
def mqtt_send(json_in):
    paho.mqtt.publish.single("WRA/device_name/al_data", json.dumps(json_in), hostname="127.0.0.1")
    
def run_logger(cmd):
    p = subprocess.Popen(cmd,
                         stdout=subprocess.PIPE,
                         stderr=subprocess.STDOUT)
    
    # Parse lines one by one
    for line in iter(p.stdout.readline, b''):
        line = line.rstrip()
        
        # Output line to console
        print ">>>> " + line
        
        # Check if the line starts with the gateway ID
        if line.startswith(PKT_DATA):
            line_json = lora_parse(line)

            # Only send valid messages
            if line_json:
                mqtt_send(line_json)
        

###############################################################################
# Main program
###############################################################################

run_logger(["util_pkt_logger"])
#run_logger(["cat", "pkt_logger.txt"])

###############################################################################
# Tests
###############################################################################

# Test reading a split log file (filtered output)
def test_split_log_read():
    try:
        fd = open("split_pkt_logger.txt", "r")
        contents = fd.read()
        fd.close()
        
        contents_json = json.dumps(lora_parse(contents))
        
    except IOError:
        print "ERROR - Cannot read file"
        
    return contents_json

# Test reading a RAW log file (actual output)
def test_log_read():
    run_logger(["cat", "pkt_logger.txt"])
    
    
# Run all tests
def test_run():
    # Parse log file and convert to JSON
    contents_json = test_split_log_read()
    print contents_json
    
    # Parse actual output from program
    test_log_read()
    
    # Send to MQTT
    paho.mqtt.publish.single("WRA/device_name/my_data", contents_json, hostname="127.0.0.1")
