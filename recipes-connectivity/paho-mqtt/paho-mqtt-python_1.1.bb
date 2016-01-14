DESCRIPTION = "Paho MQTT - user libraries for the MQTT and MQTT-SN protocols - Python version"
DESCRIPTION = "Client implementation of open and standard messaging protocols for Machine-to-Machine (M2M) and Internet of Things (IoT)."
HOMEPAGE = "http://www.eclipse.org/paho/"
SECTION = "console/network"
LICENSE = "EPL-1.0 | EDL-1.0"

DEPENDS = "python"
RDEPENDS_${PN} = "python-setuptools"

LIC_FILES_CHKSUM = " \
        file://edl-v10;md5=c09f121939f063aeb5235972be8c722c \
        file://epl-v10;md5=8d383c379e91d20ba18a52c3e7d3a979 \
"

PR = "r0"

SRC_URI = "git://git.eclipse.org/gitroot/paho/org.eclipse.paho.mqtt.python.git"

SRCREV = "73b85d2427e196d6ad6b4efb432c714bb3dab543"

S = "${WORKDIR}/git"

inherit distutils pkgconfig

