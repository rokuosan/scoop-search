package io.github.rokuosan.scoop_search.entity

import kotlinx.serialization.Serializable

@Serializable
data class Search(
    val count: Boolean = true,
    val search: String,
    val searchMode: String = "all",
    val filter: String = "Metadata/OfficialRepositoryNumber eq 1 and Metadata/DuplicateOf eq null",
    val select: String = "Id,Name",
    val skip: Int = 0,
    val top: Int = 20,
)


//{
//  "count": true,
//  "search": "nodejs",
//  "searchMode": "all",
//  "filter": "Metadata/OfficialRepositoryNumber eq 1 and Metadata/DuplicateOf eq null",
//  "orderby": "search.score() desc, Metadata/OfficialRepositoryNumber desc, NameSortable asc",
//  "skip": 0,
//  "top": 20,
//  "select": "Id,Name,NamePartial,NameSuffix,Description,Homepage,License,Version,Metadata/Repository,Metadata/FilePath,Metadata/OfficialRepository,Metadata/RepositoryStars,Metadata/Committed,Metadata/Sha",
//  "highlight": "Name,NamePartial,NameSuffix,Description,Version,License,Metadata/Repository",
//  "highlightPreTag": "<mark>",
//  "highlightPostTag": "</mark>"
//}