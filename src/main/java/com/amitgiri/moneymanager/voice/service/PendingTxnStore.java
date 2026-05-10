
package com.amitgiri.moneymanager.voice.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.amitgiri.moneymanager.voice.dto.ParsedTxnDto;

@Service
public class PendingTxnStore {

    private final Map<String, ParsedTxnDto>
            store = new ConcurrentHashMap<>();

    public void save(
            String id,
            ParsedTxnDto dto
    ) {
        store.put(id, dto);
    }

    public ParsedTxnDto get(
            String id
    ) {
        return store.get(id);
    }

    public void remove(
            String id
    ) {
        store.remove(id);
    }
}