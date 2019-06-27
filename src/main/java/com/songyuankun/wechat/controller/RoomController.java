package com.songyuankun.wechat.controller;

import com.songyuankun.wechat.common.Response;
import com.songyuankun.wechat.dao.Room;
import com.songyuankun.wechat.repository.RoomRepository;
import com.songyuankun.wechat.request.RoomForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author songyuankun
 */
@RestController
@RequestMapping("room")
@Slf4j
public class RoomController {
    private final RoomRepository roomRepository;

    public RoomController(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @PostMapping("save")
    public Response<Room> save(RoomForm room) {
        Response<Room> response = new Response<>();
        roomRepository.save(room);
        response.setData(room);
        return response;
    }

    @PostMapping("all")
    public Response<List<Room>> all() {
        Response<List<Room>> response = new Response<>();
        List<Room> all = roomRepository.findAll();
        response.setData(all);
        return response;
    }

}
