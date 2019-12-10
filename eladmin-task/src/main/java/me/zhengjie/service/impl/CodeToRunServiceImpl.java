package me.zhengjie.service.impl;

import me.zhengjie.service.CodeToRunService;
import me.zhengjie.utils.compiler_post_jar.UploadRun;

import java.io.IOException;

public class CodeToRunServiceImpl implements CodeToRunService {

    @Override
    public void codeToRun(String code) throws IOException {
        UploadRun.UploadRun(code);
    }
}
