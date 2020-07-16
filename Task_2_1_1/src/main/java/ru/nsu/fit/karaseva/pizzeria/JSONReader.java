package ru.nsu.fit.karaseva.pizzeria;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

class JSONReader {

  List<BakerConfig> readBakers(File fileBakers) {
    try {
      ObjectMapper mapper = new ObjectMapper();
      return Arrays.asList(mapper.readValue(fileBakers, BakerConfig[].class));
    } catch (IOException e) {
      e.printStackTrace();
    }

    return null;
  }

  List<DeliveryWorkerConfig> readDeliveryWorkers(File fileDeliveryWorkers) {
    try {
      ObjectMapper mapper = new ObjectMapper();
      return Arrays.asList(mapper.readValue(fileDeliveryWorkers, DeliveryWorkerConfig[].class));
    } catch (IOException e) {
      e.printStackTrace();
    }

    return null;
  }

}

