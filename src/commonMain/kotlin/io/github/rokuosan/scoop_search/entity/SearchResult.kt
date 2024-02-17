package io.github.rokuosan.scoop_search.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchResult(
    @SerialName("@odata.count")
    val count: Int? = null,

    @SerialName("@odata.context")
    val context: String? = null,

    @SerialName("@search.nextPageParameters")
    val nextPageParameters: NextPageParameters? = null,

    @SerialName("value")
    val value: List<MatchedApplication> = emptyList()
)

@Serializable
data class NextPageParameters(
    val skip: Int,
    val search: String
)


@Serializable
data class MatchedApplication(
    @SerialName("@search.score")
    val score: Double,

    @SerialName("Id")
    val id: String? = null,

    @SerialName("Name")
    val name: String? = null,

    @SerialName("NamePartial")
    val namePartial: String? = null,

    @SerialName("NameSuffix")
    val nameSuffix: String? = null,

    @SerialName("Description")
    val description: String? = null,

    @SerialName("Homepage")
    val homepage: String? = null,

    @SerialName("License")
    val license: String? = null,

    @SerialName("Version")
    val version: String? = null,

    @SerialName("Metadata")
    val metadata: MatchedApplicationMetadata? = null
)

@Serializable
data class MatchedApplicationMetadata(
    @SerialName("Repository")
    val repository: String? = null,

    @SerialName("OfficialRepository")
    val isOfficialRepository: Boolean? = null,

    @SerialName("OfficialRepositoryNumber")
    val officialRepositoryNumber: Int? = null,

    @SerialName("RepositoryStars")
    val stars: Int? = null,

    @SerialName("FilePath")
    val filepath: String? = null,

    @SerialName("Committed")
    val committed: String? = null,

    @SerialName("Sha")
    val sha: String? = null,

    @SerialName("DuplicateOf")
    val duplicateOf: String? = null,
)