package com.peter.webkeysnews.pojo

data class JsonNestedResponse(var articles :List<Article>,
var status:String,var totalResults : Int)