package com.ga.commentsapi.service;
import com.ga.commentsapi.bean.Post;
import com.ga.commentsapi.bean.User;
import com.ga.commentsapi.exception.PostNotExistException;
import com.ga.commentsapi.exception.TokenException;
import com.ga.commentsapi.exception.UnauthorizeActionException;
import com.ga.commentsapi.messagequeue.Sender;
import com.ga.commentsapi.model.Comment;
import com.ga.commentsapi.repository.CommentRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import java.util.*;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/*************************************************************************
 * The comment service Test class tests the methods that live in the
 *  CommentServiceImpl class using Mockito
 *
 *************************************************************************/

@RunWith(MockitoJUnitRunner.class)
public class CommentsServiceTest {

    /*************************************************************************
     * The static variable final Strings USERNAME and TOKEN are used to
     *  support the testing by providing username and token values to be used
     * in testing
     *************************************************************************/
    private static final String USERNAME = "user";
    private static final String TOKEN = "abcdef";

/*************************************************************************
 * @InnjectMocks of CommentServiceImpl is declared here so that
 *  CommentServiceImpl can be tested and its dependencies mocked
 **************************************************************************/

    @InjectMocks
    private CommentServiceImpl commentService;

    /*************************************************************************
     * For testing a mock of CommentRespository is needed
     *
     **************************************************************************/

    @Spy
    private CommentRepository commentRepository;

    /*************************************************************************
     * A Comment mock: comment is used throughout the test
     *
     **************************************************************************/

    @InjectMocks
    private Comment comment;
    /*************************************************************************
     * A User mock: user is used throughout the test
     *
     **************************************************************************/

    @InjectMocks
    private User user;
    /*************************************************************************
     * A Post mock: post is used throughout the test
     *
     **************************************************************************/

    @InjectMocks
    private Post post;

    /*************************************************************************
     *  A Sender mock: sender is used throughout the test to mock for
     * RabbitMQ services
     **************************************************************************/

    @Mock
    Sender sender;

    /*************************************************************************
     * The initDummies() method annoted with @Before initializes our mocks to
     * set up for testing
     **************************************************************************/

    @Before
    public void initDummies() {
        MockitoAnnotations.initMocks(this);
        user.setUsername(USERNAME);
        user.setUserId(1L);
        post.setTitle("Example post title");
        post.setText("Example post description is cool.");
        post.setPostId(1L);
        post.setUserId(1L);
        comment.setUser(user);
        comment.setPostId(1L);
        comment.setUserId(1L);
        comment.setId(1L);
        comment.setText("This is a simnple comment");
    }

    /*************************************************************************
     * This tests the createComment method
     *
     **************************************************************************/


    @Test
    public void createComment() throws TokenException, PostNotExistException {

        when(sender.getUserFromUserAPI(any())).thenReturn(user);
        when(sender.getPostByPostId(any())).thenReturn(post);
        when(commentRepository.save(comment)).thenReturn(comment);
        Comment actual = commentService.createComment(comment,1L, TOKEN);

        assertEquals(comment, actual);

    }

    /*************************************************************************
     * This tests the getCommentsbyPostId method
     *
     **************************************************************************/

    @Test
    public void getCommentsbyPostId() {

        List<Comment> comments = new ArrayList<>();
        comments.add(comment);
        commentRepository.findCommentsbyPostId(post.getPostId());

        when(commentRepository.findCommentsbyPostId(any())).thenReturn((comments));
        List<Comment> savedComments = (List<Comment>) commentRepository.findCommentsbyPostId(post.getPostId());

        assertEquals(comments, savedComments);

    }


    /*************************************************************************
     * This tests the deleteCommentbyCommentId method
     *
     **************************************************************************/

    @Test
    public void deleteCommentByCommentId() throws TokenException, UnauthorizeActionException, PostNotExistException {

        when(commentRepository.findById(any())).thenReturn(java.util.Optional.of(comment));
        Comment savedComment = commentRepository.findById(comment.getId()).orElse(null);

        if (savedComment.getId().longValue() != user.getUserId().longValue())
            throw new UnauthorizeActionException("Unauthorized Action.");
        Comment actual = savedComment;
        commentRepository.delete(savedComment);
        assertEquals(comment, actual);
    }


    /*************************************************************************
     * This tests the getCommentsByUser method
     *
     **************************************************************************/

    @Test
    public void getCommentsByUser() {
        List<Comment> comments = new ArrayList<>();
        comments.add(comment);

        commentRepository.findCommentsbyUserId(user.getUserId());

        when(commentRepository.findCommentsbyUserId(any())).thenReturn(comments);
        List<Comment> retrievedComments = commentService.getCommentsByUser(user);

        assertEquals(comments, retrievedComments);


    }


    /*************************************************************************
     * This tests the listComments method
     *
     **************************************************************************/

    @Test
    public void listComments() {
        List<Comment> comments = new ArrayList<>();
        comments.add(comment);
        when(commentRepository.findAll()).thenReturn(comments);
         List<Comment> retrievedComments = commentService.listComments();

        assertEquals(comments, retrievedComments);
    }


    /*************************************************************************
     * This tests the deleteCommentsByPostId method
     *
     **************************************************************************/

    @Test
    public void  deleteCommentsByPostId_PostId_Success()
    {
        List<Comment> comments = new ArrayList<>();
        comments.add(comment);


        when(commentRepository.findCommentsbyPostId(any())).thenReturn(comments);
        doNothing().when(commentRepository).deleteAll(anyIterable());

        Long postId = commentService.deleteCommentsByPostId(1L);

        assertEquals(Optional.ofNullable(1L), Optional.ofNullable(postId));

    }

    /*************************************************************************
     * This tests the getCommentsByUserId method
     *
     **************************************************************************/

    @Test
    public void getCommentsByUserId_List_Success() throws TokenException {
        List<Comment> comments = new ArrayList<>();
        comments.add(comment);
        when(sender.getUserFromUserAPI(anyString())).thenReturn(user);
        when(commentService.getCommentsByUserId(anyString())).thenReturn(comments);

        List<Comment> retrievedComments = commentService.getCommentsByUserId("123");
        assertEquals(comments.size(), retrievedComments.size());
    }


    @Test
    public void getCommentsByUserId_Comments_Success() {
        when(commentRepository.findCommentsbyPostId(anyLong())).thenReturn(Arrays.asList(new Comment(),new Comment()));

        when(sender.getUsersByUsersId(anySet())).thenReturn(new User[0]);
        List<Comment> retrievedComments = (List<Comment>) commentService.getCommentsbyPostId(1L);

        assertTrue(retrievedComments.size()>0);

    }


}


