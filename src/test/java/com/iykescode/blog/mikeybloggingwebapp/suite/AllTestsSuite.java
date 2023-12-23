package com.iykescode.blog.mikeybloggingwebapp.suite;

import com.iykescode.blog.mikeybloggingwebapp.controller.*;
import com.iykescode.blog.mikeybloggingwebapp.service.*;
import com.iykescode.blog.mikeybloggingwebapp.util.ImageUtilTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({

        // Controllers
        BlogControllerTest.class,
        CategoryControllerTest.class,
        CommentControllerTest.class,
        PostControllerTest.class,
        ProfileControllerTest.class,
        UserCommentControllerTest.class,
        UserControllerTest.class,

        // Services
        CategoryServiceTest.class,
        CommentServiceTest.class,
        PersonImageServiceTest.class,
        PersonProfileServiceTest.class,
        PersonServiceTest.class,
        PostImageServiceTest.class,
        PostServiceTest.class,
        PostViewServiceTest.class,
        TagServiceTest.class,

        // Utils
        ImageUtilTest.class
})
public class AllTestsSuite {
}
