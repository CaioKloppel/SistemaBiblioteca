package org.example.jsonData;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class JsonDatabase<T> {
    private final String filename;
    private final ObjectMapper mapper;
    private final Class<T> type;

    public JsonDatabase(String filename, Class<T> type) {
        this.filename = filename;
        this.mapper = new ObjectMapper();
        this.type = type;
    }

    // Salvar dados no arquivo JSON
    private void saveData(List<T> data) throws IOException {
        mapper.writeValue(new File(filename), data);
    }

    // Ler dados do arquivo JSON
    public List<T> loadData() throws IOException {
        File file = new File(filename);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        CollectionType listType = mapper.getTypeFactory().constructCollectionType(ArrayList.class, type);
        return mapper.readValue(file, listType);
    }

    // Adicionar um novo item
    public void addItem(T item) throws IOException {
        List<T> data = loadData();
        data.add(item);
        saveData(data);
    }

    public void updateItem(String id, T itemAtualizado) throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        List<T> data = loadData();
        for (int i = 0; i < data.size(); i++) {
            T item = data.get(i);
            if (item.getClass().getMethod("getNome").invoke(item).equals(id)) {
                data.set(i, itemAtualizado);
                break;
            }
        }
        saveData(data);
    }

    // Remover item
    public void removeItem(String id) throws IOException {
        List<T> data = loadData();
        data.removeIf(item -> {
            try {
                return item.getClass().getMethod("getNome").invoke(item).equals(id);
            } catch (Exception e) {
                return false;
            }
        });
        saveData(data);
    }


}
