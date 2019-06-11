<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>

<h1>Todos List</h1>


<div id="app">
  <input name="title"  v-model="message" />
  <input type="submit" id="add_todo" v-on:click="addTodo" />
  <Todos
    v-for="todo in todos"
    v-bind:todo="todo"
  >
  </Todos>
</div>

<script>


new Vue({
  el: '#app',
  data: {
    todos: [],
    title: ''
  },
  mounted: function () {
    fetch("/todos")
     .then(response => response.json())
     .then(todos => {
        this.todos = todos
     })
  },
  methods: {
    addTodo: function() {
       params = {"title": this.title}
       options= {
            "method": "POST",
            "body": JSON.stringify(params)
       }
       fetch("/addTodo", options)
    }
  }
})

Vue.component('Todos', {
  props: ['todo'],
  template: '<li>{{todo.title}} {{todo.status}}</li>',
})


</script>