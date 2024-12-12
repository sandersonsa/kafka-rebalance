// package xyz.sandersonsa.kafkaproducer;

// import java.util.Arrays;
// import java.util.LinkedHashSet;
// import java.util.List;
// import java.util.Random;

// public class Principal {

//     public static void main(String[] args) {
//         System.out.println("Hello World");
//         LinkedHashSet<String> hashSet = new LinkedHashSet<String>();
//         System.out.println(hashSet.add("78d9b835-c682-4025-8837-ea1453099538"));
//         System.out.println(hashSet.add("78d9b835-c682-4025-8837-ea1453099538"));

//         Principal principal = new Principal();
//         principal.givenList_whenNumberElementsChosen_shouldReturnRandomElementsNoRepeat();
        
//         // if (hashSet.add(consumerRecord.value())) {
//         //     hashSet.add(consumerRecord.value());
//         // } else {
//         //     LOG.info("Duplicate message : {}", consumerRecord.value());
//         // }
//     }

//     public void givenList_whenNumberElementsChosen_shouldReturnRandomElementsNoRepeat() {
//         Random rand = new Random();
//         List<String> frutas = Arrays.asList("maça", "laranja", "maria");

//         for (int i = 0; i < 25; i++) {
//             int randomIndex = rand.nextInt(frutas.size());
//             String randomElement = frutas.get(randomIndex);
//             System.out.println(randomElement);
//         }
//     }

// }
