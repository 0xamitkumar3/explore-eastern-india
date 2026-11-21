
package com.explore.exploreeasternindia.config;

import com.explore.exploreeasternindia.destination.entity.Destination;
import com.explore.exploreeasternindia.destination.repository.DestinationRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner loadData(DestinationRepository repository) {
        return args -> {

            // 1. Bodh Gaya
            if (!repository.existsByName("Bodh Gaya")) {
                Destination destination = new Destination();
                destination.setName("Bodh Gaya");
                destination.setState("Bihar");
                destination.setDistrict("Gaya");
                destination.setCategory("Religious");
                destination.setDescription("A major Buddhist pilgrimage site where Gautama Buddha attained enlightenment.");
                destination.setBestTimeToVisit("October to March");
                destination.setEntryFee(BigDecimal.ZERO);
                repository.save(destination);
            }

            // 2. Vaishali
            if (!repository.existsByName("Vaishali")) {
                Destination destination = new Destination();
                destination.setName("Vaishali");
                destination.setState("Bihar");
                destination.setDistrict("Vaishali");
                destination.setCategory("Historical");
                destination.setDescription("An ancient city associated with Lord Buddha and Lord Mahavira.");
                destination.setBestTimeToVisit("October to March");
                destination.setEntryFee(BigDecimal.ZERO);
                repository.save(destination);
            }

            // 3. Nalanda Mahavihara
            if (!repository.existsByName("Nalanda Mahavihara")) {
                Destination destination = new Destination();
                destination.setName("Nalanda Mahavihara");
                destination.setState("Bihar");
                destination.setDistrict("Nalanda");
                destination.setCategory("Historical");
                destination.setDescription("An ancient centre of learning and a UNESCO World Heritage Site.");
                destination.setBestTimeToVisit("October to March");
                destination.setEntryFee(BigDecimal.ZERO);
                repository.save(destination);
            }

            // 4. Rajgir
            if (!repository.existsByName("Rajgir")) {
                Destination destination = new Destination();
                destination.setName("Rajgir");
                destination.setState("Bihar");
                destination.setDistrict("Nalanda");
                destination.setCategory("Historical");
                destination.setDescription("A historic town known for its hills, ancient ruins and Buddhist heritage.");
                destination.setBestTimeToVisit("October to March");
                destination.setEntryFee(BigDecimal.ZERO);
                repository.save(destination);
            }

            // 5. Pawapuri
            if (!repository.existsByName("Pawapuri")) {
                Destination destination = new Destination();
                destination.setName("Pawapuri");
                destination.setState("Bihar");
                destination.setDistrict("Nalanda");
                destination.setCategory("Religious");
                destination.setDescription("An important Jain pilgrimage site associated with Lord Mahavira.");
                destination.setBestTimeToVisit("October to March");
                destination.setEntryFee(BigDecimal.ZERO);
                repository.save(destination);
            }

            // 6. Kesaria Stupa
            if (!repository.existsByName("Kesaria Stupa")) {
                Destination destination = new Destination();
                destination.setName("Kesaria Stupa");
                destination.setState("Bihar");
                destination.setDistrict("East Champaran");
                destination.setCategory("Historical");
                destination.setDescription("An ancient Buddhist stupa known for its historical and archaeological significance.");
                destination.setBestTimeToVisit("October to March");
                destination.setEntryFee(BigDecimal.ZERO);
                repository.save(destination);
            }

            // 7. Maner Sharif
            if (!repository.existsByName("Maner Sharif")) {
                Destination destination = new Destination();

                destination.setName("Maner Sharif");
                destination.setState("Bihar");
                destination.setDistrict("Patna");
                destination.setCategory("Religious");
                destination.setDescription("A historic Sufi pilgrimage site famous for the shrine of Hazrat Makhdoom Yahya Maneri.");
                destination.setBestTimeToVisit("October to March");
                destination.setEntryFee(BigDecimal.ZERO);

                repository.save(destination);
            }

            // 8. Barabar Caves
            if (!repository.existsByName("Barabar Caves")) {
                Destination destination = new Destination();

                destination.setName("Barabar Caves");
                destination.setState("Bihar");
                destination.setDistrict("Jehanabad");
                destination.setCategory("Historical");
                destination.setDescription("Ancient rock-cut caves dating back to the Mauryan period, known for their remarkable architecture.");
                destination.setBestTimeToVisit("October to March");
                destination.setEntryFee(BigDecimal.ZERO);

                repository.save(destination);
            }

            // 9. Vikramshila Ruins
            if (!repository.existsByName("Vikramshila Ruins")) {
                Destination destination = new Destination();

                destination.setName("Vikramshila Ruins");
                destination.setState("Bihar");
                destination.setDistrict("Bhagalpur");
                destination.setCategory("Historical");
                destination.setDescription("The archaeological remains of an ancient Buddhist learning centre established during the Pala dynasty.");
                destination.setBestTimeToVisit("October to March");
                destination.setEntryFee(BigDecimal.ZERO);

                repository.save(destination);
            }

            // 10. Jal Mandir
            if (!repository.existsByName("Jal Mandir")) {
                Destination destination = new Destination();

                destination.setName("Jal Mandir");
                destination.setState("Bihar");
                destination.setDistrict("Nalanda");
                destination.setCategory("Religious");
                destination.setDescription("A beautiful Jain temple situated in the middle of a sacred water pond at Pawapuri.");
                destination.setBestTimeToVisit("October to March");
                destination.setEntryFee(BigDecimal.ZERO);

                repository.save(destination);
            }

            // 11. Mundeshwari Temple
            if (!repository.existsByName("Mundeshwari Temple")) {
                Destination destination = new Destination();

                destination.setName("Mundeshwari Temple");
                destination.setState("Bihar");
                destination.setDistrict("Kaimur");
                destination.setCategory("Religious");
                destination.setDescription("An ancient hilltop temple dedicated to Lord Shiva and Goddess Shakti, known for its historical significance.");
                destination.setBestTimeToVisit("October to March");
                destination.setEntryFee(BigDecimal.ZERO);

                repository.save(destination);
            }
        };
    }
}
