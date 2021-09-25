package com.orders.adapter

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.core.io.ClassPathResource
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification
import org.springframework.data.cassandra.core.cql.keyspace.DataCenterReplication
import org.springframework.data.cassandra.core.cql.session.init.KeyspacePopulator
import org.springframework.data.cassandra.core.cql.session.init.ResourceKeyspacePopulator
import org.springframework.data.cassandra.core.mapping.CassandraMappingContext
import org.springframework.data.cassandra.core.mapping.NamingStrategy
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories

@Configuration
@EnableCassandraRepositories
@EntityScan
@PropertySource("classpath:ca-orders-cassandra-adapter.properties")
class SpringCassandraConfiguration : AbstractCassandraConfiguration() {

    @Value("\${spring.data.cassandra.local-datacenter}")
    private lateinit var datacenter: String

    @Value("\${spring.data.cassandra.keyspace-name}")
    private lateinit var keySpaceName: String

    override fun getKeyspaceCreations(): MutableList<CreateKeyspaceSpecification> {
        return mutableListOf(
            CreateKeyspaceSpecification.createKeyspace(keyspaceName)
                .ifNotExists()
                .withNetworkReplication(DataCenterReplication.of(datacenter, 1))
        )
    }

    override fun getKeyspaceName(): String {
        return keySpaceName
    }

    override fun keyspacePopulator(): KeyspacePopulator {
        return ResourceKeyspacePopulator(ClassPathResource("db/migration/V1.00__initial_db_setup.cql"))
    }

    override fun cassandraMapping(): CassandraMappingContext {
        val context = super.cassandraMapping()
        context.setNamingStrategy(NamingStrategy.SNAKE_CASE)
        return context
    }
}