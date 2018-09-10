package com.toy.spring_batch_demo;

import com.fasterxml.jackson.core.JsonParseException;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;

import javax.persistence.EntityManagerFactory;
import java.io.File;
import java.io.Writer;


public class MessageMigrationJobConfiguration {

    private int CHUNK_SIZE = 50;
    private int SKIP_LIMIT = 20;
    private static final String MESSAGE_FILE = "a";

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    // 创建一个job的Bean， 这个job只有一个step
    @Bean
    public Job messageMigrationJob(@Qualifier("messageMigrationStep") Step messageMigrationStep) {
        return jobBuilderFactory.get("messageMigrationJob").start(messageMigrationStep).build();
    }

    // 创建 step
    @Bean
    public Step messageMigrationStep(@Qualifier("jsonMessageReader") FlatFileItemReader<Message> jsonMessageReader,
                                     @Qualifier("messageItemWriter") JpaItemWriter<Message> messageItemWriter,
                                     @Qualifier("errorWrite") Writer errorWriter) {

        return stepBuilderFactory.get("messageMigrationStep").<Message, Message>chunk(CHUNK_SIZE).
                reader(jsonMessageReader).faultTolerant().skip(JsonParseException.class).skipLimit(SKIP_LIMIT)
                .listener(new MessageItemReadListener(errorWriter))
                .writer(messageItemWriter).faultTolerant().skip(Exception.class).skipLimit(SKIP_LIMIT)
                .listener(new MessageWriteListener())
                .build();
    }


    // 创建Reader，从数据源读取数据
    @Bean
    public FlatFileItemReader<Message> jsonMessageReader() {
        FlatFileItemReader<Message> reader = new FlatFileItemReader<>();
        reader.setResource(new FileSystemResource(new File(MESSAGE_FILE)));
        reader.setLineMapper(new MessageLineMapper());
        return reader;
    }

    // 创建writer， 写数据到数据库
    @Bean
    public JpaItemWriter<Message> messageItemWriter(){
        JpaItemWriter<Message> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }


}
