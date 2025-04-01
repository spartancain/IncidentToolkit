package gov.emercom.incidenttoolkit.incident

data class IncidentButton(val activityName: String, val buttonName: String) {

    override fun toString(): String {
        return buildString {
            append("IncidentButtons{")
            append("activityName=")
            append(activityName)
            append(", buttonName='")
            append(buttonName)
            append("}")
        }
    }

}


