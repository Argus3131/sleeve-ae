package com.yan.missyou.dto;

import com.yan.missyou.model.User;
import com.yan.missyou.validators.ScopeLevel;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.HashMap;

/**
 * @author Argus
 * @className LocalUser
 * @description: TODO
 * @date 2020/3/30 12:24
 * @Version V1.0
 */
public class LocalUser {
    public static ThreadLocal<HashMap<String,Object>> localUser = new ThreadLocal<>();


    public static void setLocalUser(@NotNull User user,@Positive Integer scope){
        HashMap<String,Object> map = new HashMap<>();
        map.put("user",user);
        map.put("scope",scope);
        LocalUser.localUser.set(map);
    }


    public static void clear() {
        LocalUser.localUser.remove();
    }

    public static User getUser() {
        HashMap<String, Object> map = LocalUser.localUser.get();
        User user = (User)map.get("user");
        return user;
    }


    public static Integer getScope() {
        HashMap<String, Object> map = LocalUser.localUser.get();
        Integer scope = (Integer)map.get("scope");
        return scope;
    }
   
}