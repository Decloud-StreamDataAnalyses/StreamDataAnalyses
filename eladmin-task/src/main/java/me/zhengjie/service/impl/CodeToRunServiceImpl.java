package me.zhengjie.service.impl;

import me.zhengjie.service.CodeToRunService;
import me.zhengjie.utils.compiler_post_jar.UploadRun;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class CodeToRunServiceImpl implements CodeToRunService {

    @Override
    public void codeToRun(String code) throws IOException {
        UploadRun.UploadRun(code);
    }
}
