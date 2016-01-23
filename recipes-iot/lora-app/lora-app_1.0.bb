SUMMARY = "LoRa packet parser. Sends data to HDC"

SRC_URI = "file://lora_app.py"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://lora_app.py;beginline=3;endline=14;md5=ff620a27ab0c6eb3b80825033a352d8e"

RDEPENDS_${PN} = "python"

#INSANE_SKIP_${PN} += "installed-vs-shipped"

S = "${WORKDIR}"

# TODO: move. Set here because of Lora concentrator config
PREFIX = "/home/root"

do_install() {
	install -d ${D}/${PREFIX}

	install -m 644  ${S}/lora_app.py ${D}/${PREFIX}
}

FILES_${PN} = "${PREFIX}/*.py"
