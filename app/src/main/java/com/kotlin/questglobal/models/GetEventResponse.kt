package com.kotlin.questglobal.models

data class GetEventResponse(
	val code: Int? = null,
	val data: Data? = null,
	val message: String? = null,
	val id: String? = null
)

data class Data(
	val email: String? = null,
	val token: String? = null,
	val id: String? = null,
	val name: String? = null
)

