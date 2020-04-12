package com.menlhk.akps.model


import com.google.gson.annotations.SerializedName

data class UsulanResponse(
    @SerializedName("auth")
    var auth: Any? = Any(), // null
    @SerializedName("description")
    var description: String? = "",
    @SerializedName("events")
    var events: Any? = Any(), // null
    @SerializedName("folders")
    var folders: List<Any?>? = listOf(),
    @SerializedName("folders_order")
    var foldersOrder: List<Any?>? = listOf(),
    @SerializedName("id")
    var id: String? = "", // 0058dd0b-b315-4bd4-8576-a9fc442ae74a
    @SerializedName("name")
    var name: String? = "", // AKPS API
    @SerializedName("order")
    var order: List<String?>? = listOf(),
    @SerializedName("requests")
    var requests: List<Request?>? = listOf(),
    @SerializedName("variables")
    var variables: List<Any?>? = listOf()
) {
    data class Request(
        @SerializedName("auth")
        var auth: Any? = Any(), // null
        @SerializedName("data")
        var `data`: List<Data?>? = listOf(),
        @SerializedName("dataMode")
        var dataMode: String? = "", // params
        @SerializedName("description")
        var description: String? = "",
        @SerializedName("events")
        var events: Any? = Any(), // null
        @SerializedName("folder")
        var folder: Any? = Any(), // null
        @SerializedName("headerData")
        var headerData: Any? = Any(), // null
        @SerializedName("id")
        var id: String? = "", // f4560eca-b982-4516-a501-eaf11d5bbdd4
        @SerializedName("method")
        var method: String? = "", // GET
        @SerializedName("name")
        var name: String? = "", // localhost/akps-android/public/api/kawasan
        @SerializedName("pathVariableData")
        var pathVariableData: List<Any?>? = listOf(),
        @SerializedName("pathVariables")
        var pathVariables: PathVariables? = PathVariables(),
        @SerializedName("queryParams")
        var queryParams: List<Any?>? = listOf(),
        @SerializedName("url")
        var url: String? = "" // localhost/akps-android/public/api/kawasan
    ) {
        data class Data(
            @SerializedName("description")
            var description: String? = "",
            @SerializedName("enabled")
            var enabled: Boolean? = false, // true
            @SerializedName("key")
            var key: String? = "", // password
            @SerializedName("type")
            var type: String? = "", // text
            @SerializedName("value")
            var value: String? = "" // 123123
        )

        class PathVariables(
        )
    }
}