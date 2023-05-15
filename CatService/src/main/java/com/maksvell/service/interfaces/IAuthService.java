package com.maksvell.service.interfaces;

import com.maksvell.dto.requests.AuthDto;
import com.maksvell.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface IAuthService {
    User registration(AuthDto authDto);

    User login(AuthDto authDto);
}
