package com.songyuankun.wechat.controller;

import com.songyuankun.wechat.common.Response;
import com.songyuankun.wechat.dao.Room;
import com.songyuankun.wechat.repository.RoomRepository;
import com.songyuankun.wechat.request.RoomForm;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author songyuankun
 */
@Api
@RestController
@RequestMapping("room")
@Slf4j
public class RoomController {
    private final RoomRepository roomRepository;

    public RoomController(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @PostMapping("save")
    public Response<Room> save(@RequestBody RoomForm roomForm) {
        Response<Room> response = new Response<>();
        Room room = new Room();
        BeanUtils.copyProperties(roomForm, room);
        room.setStatus(0);
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

    @PostMapping("page")
    public Response<Page<Room>> page(@RequestParam(defaultValue = "0") Integer pageNumber, @RequestParam(defaultValue = "10") Integer pageSize) {
        Response<Page<Room>> response = new Response<>();
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Room> all = roomRepository.findAll(pageable);
        response.setData(all);
        return response;
    }
}
