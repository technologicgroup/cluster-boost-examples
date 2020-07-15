# [cluster-boost](https://github.com/technologicgroup/cluster-boost) library examples

## Version 1.1

## Overview
Every example is a spring-boot application that starts Ignite cluster with one node. Every example shows one (or more) of the [cluster-boost](https://github.com/technologicgroup/cluster-boost) library features.

## [Example 1](https://github.com/technologicgroup/cluster-boost-examples/tree/master/example-01)
### Structure
```
.
├--domain
   ├--TestAccessor.java
   ├--TestKey.java
   ├--TestRepository.java
   ├--TestValue.java   
├--Application.java
├--ApplicationConfig.java
├--ClusterReadyConsumer.java
```
### Features
- Self-registered repositories
- Cluster ready event

### Code snippet
```java
@Repository
public class TestRepository extends CommonRepository<TestKey, TestValue> {

}
```

## [Example 2](https://github.com/technologicgroup/cluster-boost-examples/tree/master/example-02)
### Features
- Run bean as a cluster task
- Cluster ready event

### Code snippet
```java
if (cluster.isFirstNode()) {
  Collection<Integer> results = cluster.runBean(RunnableBean.class, "<Test Argument>");
  log.info("Cluster run result: {}", results);
}
```

## [Example 3](https://github.com/technologicgroup/cluster-boost-examples/tree/master/example-01)
### Features
- Chain running
- Cluster ready event

### Code snippet
```java
Collection<ChainResult<String>> results = Chain.of(cluster)
    .map(ChainBean1.class, "Chain argument")  // Start chain with string argument
    .filter(r -> r.getResult() == 1)               // Continue chain only for odd nodes
    .map(ChainBean2.class)                         // On even nodes create a string result
    .run();                                        // Run chain steps
```


## [Example 4](https://github.com/technologicgroup/cluster-boost-examples/tree/master/example-01)
### Features
- Chain running with audit
- Self-registered repositories
- Cluster ready event

### Code snippet
```java
if (cluster.isFirstNode()) {
  Collection<ChainResult<String>> results = Chain.of(cluster)
      .track(trackingId)                             // Track all chain steps with trackingId
      .map(ChainBean1.class, "Chain argument")       // Start chain with string argument
      .filter(r -> r.getResult() == 1)               // Continue chain only for odd nodes
      .map(ChainBean2.class)                         // On even nodes create a string result
      .run();                                        // Run chain steps

  log.info("Chain result: {}", results);

  // Get audit items for tracking id
  List<AuditItem> auditItems = auditService.getItems(trackingId);
  log.info("Audit items: {}", auditItems);
}
```
