package com.toy.spring_batch_demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.batch.core.ItemWriteListener;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

import static java.lang.String.format;


public class MessageWriteListener implements ItemWriteListener<Message> {

    @Autowired
    private Writer errorWriter;

    @Override
    public void beforeWrite(List<? extends Message> items) {

    }

    @Override
    public void afterWrite(List<? extends Message> items) {

    }

    @Override
    public void onWriteError(Exception exception, List<? extends Message> items) {
        try {
            errorWriter.write(format("%s%n", exception.getMessage()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Message message : items) {
            try {
                errorWriter.write(format("Failed writing message id: %s", message.getObjectId()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
