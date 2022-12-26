package ru.otus.projs.hw14.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@AllArgsConstructor
public class IdSynchronizerService {

    private final Map<EntryType, Map<Long, String>> syncIdsMap = Map.ofEntries(
            Map.entry(EntryType.author, new ConcurrentHashMap<>()),
            Map.entry(EntryType.genre, new ConcurrentHashMap<>())
    );
    private final Map<Long, String> syncGenreIds = new ConcurrentHashMap<>();

    public void putIdLink(Long idOld, String idNew, EntryType type) {
        syncIdsMap.get(type).put(idOld, idNew);
    }

    public String getIdByOld(Long oldId, EntryType type) {
        return syncIdsMap.get(type).get(oldId);
    }

    public static enum EntryType {
        author,
        genre
    }
}
