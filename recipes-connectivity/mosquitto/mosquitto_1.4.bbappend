FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI = "file://mosquitto.service"

do_install_append() {
    install -d ${D}${systemd_unitdir}/system
    install -m 0644 ${WORKDIR}/mosquitto.service ${D}${systemd_unitdir}/system/
}

