package com.aua.laundry.service;

import com.aua.laundry.entity.DeliveryNote;
import com.aua.laundry.entity.LaundryOrder;
import com.aua.laundry.entity.LaundryStatus;
import com.aua.laundry.repository.DeliveryNoteRepository;
import com.aua.laundry.repository.LaundryOrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DeliveryNoteService {

    private final DeliveryNoteRepository deliveryNoteRepository;
    private final LaundryOrderRepository laundryOrderRepository;

    public DeliveryNoteService(DeliveryNoteRepository deliveryNoteRepository,
                               LaundryOrderRepository laundryOrderRepository) {
        this.deliveryNoteRepository = deliveryNoteRepository;
        this.laundryOrderRepository = laundryOrderRepository;
    }

    public String save(DeliveryNote deliveryNote, UUID laundryOrderId) {
        Optional<LaundryOrder> optional = laundryOrderRepository.findById(laundryOrderId);
        if (optional.isEmpty()) {
            return "The laundry order with that id does not exist";
        }
        if (optional.get().getStatus() != LaundryStatus.READY) {
            return "Delivery notes are only for READY laundry orders";
        }
        deliveryNote.setDeliveryDate(LocalDate.now());
        deliveryNote.setLaundryOrder(optional.get());
        deliveryNoteRepository.save(deliveryNote);
        return "Delivery note saved successfully";
    }

    public List<DeliveryNote> getByCustomerPhone(String phone) {
        return deliveryNoteRepository.findByCustomerPhone(phone);
    }

    public String delete(UUID id) {
        if (!deliveryNoteRepository.existsById(id)) {
            return "The delivery note with that id does not exist";
        }
        deliveryNoteRepository.deleteById(id);
        return "Delivery note deleted successfully";
    }

}
