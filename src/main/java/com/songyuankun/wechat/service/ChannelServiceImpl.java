package com.songyuankun.wechat.service;

import com.songyuankun.wechat.entity.Channel;
import com.songyuankun.wechat.repository.ChannelRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author langhsu
 *
 */
@Service
@Transactional(readOnly = true)
public class ChannelServiceImpl {
	@Autowired
	private ChannelRepository channelRepository;


	public List<Channel> findAll(int status) {
		Sort sort = Sort.by(Sort.Direction.DESC, "weight", "id");
		List<Channel> list;
		if (status > -1) {
			list = channelRepository.findAllByStatus(status, sort);
		} else {
			list = channelRepository.findAll(sort);
		}
		return list;
	}


	public Map<Integer, Channel> findMapByIds(Collection<Integer> ids) {
		List<Channel> list = channelRepository.findAllById(ids);
		Map<Integer, Channel> rets = new HashMap<>();
		list.forEach(po -> rets.put(po.getId(), po));
		return rets;
	}


	public Channel getById(int id) {
		return channelRepository.findById(id).get();
	}


	@Transactional
	public void update(Channel channel) {
		Optional<Channel> optional = channelRepository.findById(channel.getId());
		Channel po = optional.orElse(new Channel());
		BeanUtils.copyProperties(channel, po);
		channelRepository.save(po);
	}


	@Transactional
	public void updateWeight(int id, int weighted) {
		Channel po = channelRepository.findById(id).get();

		int max =0;
		if (1 == weighted) {
			max = channelRepository.maxWeight() + 1;
		}
		po.setWeight(max);
		channelRepository.save(po);
	}


	@Transactional
	public void delete(int id) {
		channelRepository.deleteById(id);
	}


	public long count() {
		return channelRepository.count();
	}

}
