package com.technologicgroup.cluster.examples;

import com.technologicgroup.boost.chain.Chain;
import com.technologicgroup.boost.chain.ChainResult;
import com.technologicgroup.boost.core.Cluster;
import com.technologicgroup.boost.common.ClusterReadyEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClusterReadyConsumer implements ApplicationListener<ClusterReadyEvent> {
  private final Cluster cluster;

  @Override
  public void onApplicationEvent(@NotNull ClusterReadyEvent event) {
    log.info("Cluster is ready: {}", event);

    if (cluster.isFirstNode()) {
      Collection<ChainResult<String>> results = Chain.of(cluster)
          .map(ChainBean1.class, "Chain argument")  // Start chain with string argument
          .filter(r -> r.getResult() == 1)               // Continue chain only for odd nodes
          .map(ChainBean2.class)                         // On even nodes create a string result
          .run();                                        // Run chain steps

      log.info("Chain result: {}", results);
    }
  }
}
