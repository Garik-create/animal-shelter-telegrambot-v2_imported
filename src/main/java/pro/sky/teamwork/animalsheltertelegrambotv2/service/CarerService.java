package pro.sky.teamwork.animalsheltertelegrambotv2.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pro.sky.teamwork.animalsheltertelegrambotv2.dto.CarerRecord;
import pro.sky.teamwork.animalsheltertelegrambotv2.exception.CarerNotFoundException;
import pro.sky.teamwork.animalsheltertelegrambotv2.model.Carer;
import pro.sky.teamwork.animalsheltertelegrambotv2.repository.CarerRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class CarerService {
    private final static Logger LOGGER = LoggerFactory.getLogger(CarerService.class);
    private final CarerRepository carerRepository;

    public CarerService(CarerRepository carerRepository) {
        this.carerRepository = carerRepository;
    }

    /**
     * Добавление данных об опекуне животного
     *
     * <br>//@param setAge <b>Возраст</b>
     *
     * @see CarerRepository
     */
    @Transactional
    public Carer addCarer(CarerRecord carerRecord) {
        if (carerRecord != null) {
            Carer carer = new Carer();
            carer.setFullName(carerRecord.getSecondName() + " " +
                    carerRecord.getFirstName() + " " +
                    carerRecord.getPatronymic());
            carer.setBirthYear(LocalDate.now().getYear() - carerRecord.getAge());
            carer.setPhoneNumber(carerRecord.getPhoneNumber());
            LOGGER.info("Was invoked method for adding carer");
            return this.carerRepository.save(carer);
        } else {
            LOGGER.error("Input object 'carerRecord' is null");
            throw new IllegalArgumentException("Требуется добавить опекуна");
        }
    }

    @Transactional
    public Carer addCarer(String fullName, int age, String phoneNumber) {
        if (!fullName.isEmpty() && !fullName.isBlank() &&
                !phoneNumber.isEmpty() && !phoneNumber.isBlank()) {
            Carer carer = new Carer();
            carer.setFullName(fullName);
            carer.setBirthYear(LocalDate.now().getYear() - age);
            carer.setPhoneNumber(phoneNumber);
            LOGGER.info("Was invoked method for adding carer from Telegram bot");
            this.carerRepository.save(carer);
            return carer;
        } else {
            LOGGER.error("Carer's full name or phone number is empty");
            throw new IllegalArgumentException("Требуется указать корректные данные: имя опекуна, телефонный номер опекуна");
        }
    }

    @Transactional
    public Carer findCarer(long id) {
        if (id < 0) {
            LOGGER.error("Input id = " + id + " for getting carer is incorrect");
            throw new IllegalArgumentException("Требуется указать корректный id опекуна");
        }
        LOGGER.info("Was invoked method to find carer");
        return this.carerRepository.findById(id).
                orElseThrow(() -> new CarerNotFoundException("Опекун с id = " + id + " не найден"));
    }

    @Transactional
    public Carer editCarer(CarerRecord carerRecord) {
        if (carerRecord != null) {
            Carer carer = new Carer();
            carer.setFullName(carerRecord.getSecondName() + " " +
                    carerRecord.getFirstName() + " " +
                    carerRecord.getPatronymic());
            carer.setBirthYear(LocalDate.now().getYear() - carerRecord.getAge());
            carer.setPhoneNumber(carerRecord.getPhoneNumber());
            LOGGER.info("Was invoked method to edit carer");
            this.carerRepository.save(carer);
            return carer;
        } else {
            LOGGER.error("Input object 'carerRecord' is null");
            throw new IllegalArgumentException("Требуется добавить опекуна");
        }
    }

    @Transactional
    public void deleteCarer(long id) {
        if (id < 0) {
            LOGGER.error("Input id = " + id + " for deleting carer is incorrect");
            throw new IllegalArgumentException("Требуется указать корректный id опекуна");
        } else {
            LOGGER.info("Was invoked method to delete carer");
            this.carerRepository.deleteById(id);
        }
    }

    public boolean existsCarerByFullNameAndPhoneNumber(String fullName, String phoneNumber) {
        return this.carerRepository.existsCarerByFullNameAndPhoneNumber(fullName, phoneNumber);
    }

    public List<Carer> findAll(){
        return carerRepository.findAll ();
    }
}
