package com.cycling.dao;

import com.cycling.pojo.Ride;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface RideDao {

    /**
     * 插入骑行记录
     * @param ride
     * @return: int
     */
    int insertRide(Ride ride);

    /**
     * 删除骑行记录
     * @param id
     * @return int
     */
    int deleteRideById(Integer id);

    /**
     * 根据userId删除用户的全部骑行记录
     * @param id
     * @return
     */
    int deleteRideByUserId(Integer id);

    /**
     * 根据骑行记录的id查询单条记录
     * @param id
     * @return
     */
    Ride selectRideById(Integer id);

    /**
     * 根据用户id查询本用户的骑行记录
     * @param userId
     * @return
     */
    ArrayList<Ride> selectRideByUserId(Integer userId);


}
