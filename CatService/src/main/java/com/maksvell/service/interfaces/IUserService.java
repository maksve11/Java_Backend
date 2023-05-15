package com.maksvell.service.interfaces;

import com.maksvell.dto.UserDto;
import com.maksvell.entity.User;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@Service
public interface IUserService {
        User createUser(UserDto userDto);

        User findByEmail(String email);

        User findById(Long id);

        List<UserDto> findAllUsers();

        User getUserByPrincipal(Principal principal);

        void banUser(Long id);

        void changeUserRoles(UserDto userDto, Map<String, String> form);
}
