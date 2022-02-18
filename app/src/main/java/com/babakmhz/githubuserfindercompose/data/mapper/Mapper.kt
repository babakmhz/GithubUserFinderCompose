package com.babakmhz.githubuserfindercompose.data.mapper

interface Mapper <T, DomainModel>{

    fun mapToDomainModel(model: T): DomainModel

    fun mapFromDomainModel(domainModel: DomainModel): T
}