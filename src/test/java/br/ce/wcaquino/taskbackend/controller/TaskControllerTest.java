package br.ce.wcaquino.taskbackend.controller;

import org.junit.Test;
import br.ce.wcaquino.taskbackend.model.Task;
import br.ce.wcaquino.taskbackend.repo.TaskRepo;
import br.ce.wcaquino.taskbackend.utils.ValidationException;
import java.time.LocalDate;
import org.junit.Assert;
import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 *
 * @author cetim
 */
public class TaskControllerTest {
    
    @Mock
    private TaskRepo taskRepo;
    
    @InjectMocks
    private TaskController todoController;
    
    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void naoDeveSalvarTarefaSemDescricao() {
        try {
            Task todo = new Task();
            todo.setDueDate(LocalDate.now());
            todoController.save(todo);
        }catch(ValidationException e) {
            Assert.assertEquals("Fill the task description", e.getMessage());
        }
    }
    
    @Test
    public void naoDeveSalvarTarefaSemData() {
        try {
            Task todo = new Task();
            todo.setTask("Teste 01");
            todoController.save(todo);
        }catch(ValidationException e) {
            Assert.assertEquals("Fill the due date", e.getMessage());
        }
    }
    
    @Test
    public void naoDeveSalvarTarefaComDataPassada(){
        try {
            Task todo = new Task();
            todo.setTask("Teste 01");
            todo.setDueDate(LocalDate.of(2010,01,01));
            todoController.save(todo);
        }catch(ValidationException e) {
            Assert.assertEquals("Due date must not be in past", e.getMessage());
        }
    }
    
    @Test
    public void deveSalvarTarefaComSucesso() throws ValidationException{
            Task todo = new Task();
            todo.setTask("Teste 01");
            todo.setDueDate(LocalDate.now());
            todoController.save(todo);
            Mockito.verify(taskRepo).save(todo);
    }
 
}
