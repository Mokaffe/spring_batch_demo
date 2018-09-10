package com.toy.spring_batch_demo;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.MappingJsonFactory;
import org.springframework.batch.item.file.LineMapper;

import java.util.Map;


// LineMapper 的输入是 获取一行文本和行号，然后转换成 message
// 本例子中，一行文本就是一个json对象，所以我们使用JsonParser 来转换message
public class MessageLineMapper implements LineMapper<Message> {

    private MappingJsonFactory factory = new MappingJsonFactory();

    @Override
    public Message mapLine(String line, int lineNumber) throws Exception {
        JsonParser parser = factory.createParser(line);
        Map<String, Object> map = (Map)parser.readValuesAs(Map.class);
        Message message = new Message("id_1", "this is content");
        // ... 转换逻辑
        return message;
    }
}
