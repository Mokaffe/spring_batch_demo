package com.toy.spring_batch_demo;

import org.springframework.batch.core.ItemReadListener;

import java.io.IOException;
import java.io.Writer;

import static java.lang.String.format;

public class MessageItemReadListener implements ItemReadListener<Message> {

    private Writer errorWriter;

    public MessageItemReadListener(Writer errorWriter) {
        this.errorWriter = errorWriter;
    }

    @Override
    public void beforeRead() {

    }

    @Override
    public void afterRead(Message item) {

    }

    @Override
    public void onReadError(Exception ex){
        try {
            errorWriter.write(format("%s%n", ex.getMessage()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
