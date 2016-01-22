DESCRIPTION = "WR HDC proxy"
HOMEPAGE = "https://github.com/rlwr/hdc-mqtt-proxy.git"
LICENSE = "GPL"

SRC_URI = "git://github.com/rlwr/hdc-mqtt-proxy.git"
SRCREV = "96be565aaee33cf520fcf62586b70e69381b8ba6"


LIC_FILES_CHKSUM = "file://LICENSE.GPL;md5=751419260aa954499f7abaabaa882bbe"

DEPENDS = "wr-iot-agent paho-mqtt"

S = "${WORKDIR}/git"

inherit autotools

