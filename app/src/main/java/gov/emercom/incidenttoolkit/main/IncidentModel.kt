package gov.emercom.incidenttoolkit.main

data class IncidentPutList(
    val incidentID: Int,
    val incidentName: String,
    val incidentType: String,
    val incidentLoc: String,
    val incidentStart: Long,
    val incidentStartDTG: String,
    var isSelected: Int
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
    val incidentEndDTG: String?,
    val incidentSituation: String?,
    val incidentObjectives: String?,
    val incidentEmphasis: String?,
    val incidentSA: String?,
    val incidentSafetyPlanReq: Int?,
    val incidentSafetyPlanLoc: String?,
    val incidentRadInstructions: String?,
    val incidentRef: String?,
    val incidentMapImage: ByteArray?
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
            append(", incidentEnd=")
            append(incidentEndDTG)
            append(", incidentSituation=")
            append(incidentSituation)
            append(", incidentObjectives=")
            append(incidentObjectives)
            append(", incidentEmphasis=")
            append(incidentEmphasis)
            append(", incidentSA=")
            append(incidentSA)
            append(", incidentSafetyPlanRequired=")
            append(incidentSafetyPlanReq.toString())
            append(", incidentSafetyPlanLoc=")
            append(incidentSafetyPlanLoc)
            append(", incidentRadInstructions=")
            append(incidentRadInstructions)
            append(", incidentRef=")
            append(incidentRef)
            append(", incidentMapImage=")
            append(incidentMapImage)
            append('}')
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as IncidentGetList

        if (incidentID != other.incidentID) return false
        if (incidentMapImage != null) {
            if (other.incidentMapImage == null) return false
            if (!incidentMapImage.contentEquals(other.incidentMapImage)) return false
        } else if (other.incidentMapImage != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = incidentID
        result = 31 * result + (incidentMapImage?.contentHashCode() ?: 0)
        return result
    }
}

data class OrgList(
    val orgIndex: Int,
    val orgPosition: String,
    val orgPosType: String,
    val orgPersName: String,
    val orgIncidentID: Int
) {
    override fun toString(): String {

        return buildString {
            append("Organisation{")
            append("orgIndex=")
            append(orgIndex)
            append(", orgPosition=")
            append(orgPosition)
            append('\'')
            append(", orgPosType=")
            append(orgPosType)
            append(", orgPersName=")
            append(orgPersName)
            append(", orgIncidentID=")
            append(orgIncidentID)
            append('}')
        }
    }
}

data class OrgChartList(
    val orgPosition: String,
    val orgPersName: String
) {
    override fun toString(): String {

        return buildString {
            append("OrganisationChart{")
            append(", orgPosition=")
            append(orgPosition)
            append('\'')
            append(", orgPersName=")
            append(orgPersName)
            append('}')
        }
    }
}

data class PersMinList(
    val persIndex: Int,
    val persName: String,
    val persPosition: String,
    val persIncidentID: Int
) {
    override fun toString(): String {

        return buildString {
            append("Organisation{")
            append("persIndex=")
            append(persIndex)
            append(", persName=")
            append(persName)
            append('\'')
            append(", persPosition=")
            append(persPosition)
            append(", persIncidentID=")
            append(persIncidentID)
            append('}')
        }
    }
}

data class PersFullList(
    val persIndex: Int,
    val persName: String,
    val persTitle: String,
    val persPosition: String,
    val persPhone: String,
    val persRadChannel: String,
    val persCallsign: String,
    val persIncidentID: Int
) {
    override fun toString(): String {

        return buildString {
            append("Organisation{")
            append("persIndex=")
            append(persIndex)
            append(", persName=")
            append(persName)
            append('\'')
            append(", persTitle=")
            append(persTitle)
            append(", persPosition=")
            append(persPosition)
            append(", persPhone=")
            append(persPhone)
            append(", persRadChannel=")
            append(persRadChannel)
            append(", persCallsign=")
            append(persCallsign)
            append(", persIncidentID=")
            append(persIncidentID)
            append('}')
        }
    }
}

data class RadioList(
    val radIndex: Int,
    val radChannel: String,
    val radPosition: String,
    val radFunction: String,
    val radMode: String,
    val radRxFreq: String,
    val radRxTone: String,
    val radTxFreq: String,
    val radTxTone: String,
    val radRemarks: String,
    val radIncidentID: Int
) {
    override fun toString(): String {

        return buildString {
            append("Radio{")
            append("radIndex=")
            append(radIndex)
            append(", radChannel=")
            append(radChannel)
            append('\'')
            append(", radPosition=")
            append(radPosition)
            append(", radFunction=")
            append(radFunction)
            append(", radMode=")
            append(radMode)
            append(", radRxFreq=")
            append(radRxFreq)
            append(", radRxTone=")
            append(radRxTone)
            append(", radTxFreq=")
            append(radTxFreq)
            append(", radTxTone=")
            append(radTxTone)
            append(", radRemarks=")
            append(radRemarks)
            append(", radIncidentID=")
            append(radIncidentID)
            append('}')
        }
    }
}

data class TimelineList(
    val timeIndex: Int,
    var timePeriodStart: String,
    val timePeriodEnd: String,
    var timePeriodRef: String,
    val timeIncidentID: Int,
    var isSelected: Int
) {
    override fun toString(): String {

        return buildString {
            append("Timeline{")
            append("timeIndex=")
            append(timeIndex)
            append('\'')
            append(", timePeriodStart=")
            append(timePeriodStart)
            append(", timePeriodEnd=")
            append(timePeriodEnd)
            append(", timePeriodRef=")
            append(timePeriodRef)
            append(", timeIncidentID=")
            append(timeIncidentID)
            append('}')
        }
    }
}