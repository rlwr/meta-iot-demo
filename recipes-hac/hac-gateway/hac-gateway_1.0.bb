DESCRIPTION = "Helix App Cloud agent gateway"
HOMEPAGE = "http://wiki.eclipse.org/TCF"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://galileo_rocket-virtual-gateway.sh;md5=2beb45647b8cf812c7a207947ec3ace7"

SRC_URI = "file://galileo_rocket-virtual-gateway-linux-x64.zip"
SRC_URI[md5sum] = "7f48220325534203b909a66ada72a0f8"
SRC_URI[sha256sum] = "107687b159dc042f40a1b4bfdb6985a1132496af81ac09573dfc7b8c04eb19a5"

S = "${WORKDIR}/galileo_rocket-virtual-gateway"

# Program is supplied as a binary for x86_64
COMPATIBLE_HOST = '(x86_64).*-linux'

# Disable QA issues on prebuilt binaries
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
INHIBIT_PACKAGE_STRIP = "1"

RDEPENDS_${PV} = "zlib util-linux-libuuid hac"

PREFIX = "/opt/hac-gateway"

do_install() {
	install -d ${D}/${PREFIX}
  cp -rfp  ${S}/* ${D}/${PREFIX}
}

FILES_${PN} += "${PREFIX}"

