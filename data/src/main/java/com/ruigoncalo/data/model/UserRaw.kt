package com.ruigoncalo.data.model

data class UserRaw(val id: Int,
                   val name: String,
                   val username: String,
                   val email: String,
                   val address: AddressRaw,
                   val phone: String,
                   val website: String,
                   val company: CompanyRaw)

data class AddressRaw(val street: String,
                      val suite: String,
                      val city: String,
                      val zipCode: String,
                      val geo: GeoRaw)

data class GeoRaw(val lat: Double,
                  val lng: Double)

data class CompanyRaw(val name: String,
                      val catchPhrase: String,
                      val bs: String)