package com.StationManager.app.storage.database.converters;

import com.StationManager.app.domain.client.Client;
import com.StationManager.app.storage.database.dto.ClientPositions;
import jakarta.persistence.AttributeConverter;
import org.hibernate.type.descriptor.converter.internal.CollectionConverter;

//import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class QueueListConverter implements AttributeConverter<Client, ClientPositions> {

    @Override
    public ClientPositions convertToDatabaseColumn(Client attribute) {
        return null;
    }

    @Override
    public Client convertToEntityAttribute(ClientPositions dbData) {
        return null;
    }
}
