package gov.emercom.incidenttoolkit.main

data class IncidentPutList(
    val incidentID: Int,
    val incidentName: String,
    val incidentType: String,
    val incidentLoc: String,
    val incidentStart: Long,
    val incidentStartDTG: String,
    //var incidentEnd: Date
) {
    override fun toString(): String {

        return buildString {
            append("Incident{")
            append("incidentID=")
            append(incidentID)
            append(", incidentName='")
            append(incidentName)
            append('\'')
            append(", incidentType=")
            append(incidentType)
            append(", incidentLoc=")
            append(incidentLoc)
            append(", incidentStart=")
            append(incidentStart)
            append('}')
        }
    }
}

data class IncidentGetList(
    val incidentID: Int,
    val incidentName: String,
    val incidentType: String,
    val incidentLoc: String,
    val incidentStart: Long,
    val incidentStartDTG: String,
    val incidentMapImage: ByteArray?
    //var incidentEnd: Date
) {
    override fun toString(): String {

        return buildString {
            append("Incident{")
            append("incidentID=")
            append(incidentID)
            append(", incidentName='")
            append(incidentName)
            append('\'')
            append(", incidentType=")
            append(incidentType)
            append(", incidentLoc=")
            append(incidentLoc)
            append(", incidentStart=")
            append(incidentStart)
            append(", incidentMapImage=")
            append(incidentMapImage)
            append('}')
        }
    }
}