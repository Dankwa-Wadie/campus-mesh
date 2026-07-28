package edu.gctu.campusmesh.data

enum class SchoolRole {
    STUDENT,
    LECTURER,
    STAFF,
    GUEST
}

data class SchoolProfile(
    val id: String,
    val displayName: String,
    val role: SchoolRole = SchoolRole.STUDENT,
    val campusId: String = "main_campus",
    val department: String = "Faculty of Computing & CIS",
    val indexNumber: String = "",
    val isVerifiedOfficial: Boolean = false,
    val clientType: String = "Android" // "Android" or "Safari Web"
) {
    val roleBadge: String
        get() = when {
            isVerifiedOfficial -> "[GCTU Official • Verified]"
            role == SchoolRole.LECTURER -> "[Lecturer]"
            role == SchoolRole.STAFF -> "[Staff]"
            clientType.contains("Web", ignoreCase = true) -> "[Student • Web]"
            else -> "[Student]"
        }
}
