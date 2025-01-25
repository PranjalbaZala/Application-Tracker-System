package com.main_service.service;

import com.main_service.advice.Constant;
import com.main_service.model.Stream;
import com.main_service.repository.StreamRepository;
import lombok.extern.slf4j.Slf4j;
import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataNotFoundException;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@Service
@Slf4j
@Configuration
@EnableTransactionManagement
public class StreamService {
    @Autowired
    private StreamRepository streamRepository;
    @Transactional(rollbackFor = ConfigDataNotFoundException.class, readOnly = true, isolation = Isolation.READ_UNCOMMITTED)
    public Optional<List<?>> getAllStream() {
        return Optional.of(streamRepository.findAll());
    }

    public void addStream(Stream stream) {
        Stream addStream = Stream.builder()
                .streamName(stream.getStreamName())
                .createdTime(LocalDateTime.now())
                .isActive(Constant.True)
                .build();

        streamRepository.save(addStream);
        log.info("Stream {} saved", stream);
    }

    public Stream updateStream(Stream stream) throws Exception {
        Optional<Stream> streamOptional = streamRepository.findById(stream.getStreamId());
        if (!streamOptional.isPresent()) {
            throw new Exception("stream not found");
        }

        Stream existingStream = streamOptional.get();
        existingStream.setStreamName(stream.getStreamName());
        existingStream.setUpdatedTime(LocalDateTime.now());
        Stream updatedStream = streamRepository.save(existingStream);
        return updatedStream;
    }

    public void deleteStream(Long id) {
        Optional<Stream> permission = streamRepository.findById(id);
        permission.ifPresent(i -> i.setIsActive(false));
        permission.ifPresent(streamRepository::save);
    }


}
