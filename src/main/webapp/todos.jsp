<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>

<h1>Todos List</h1>

<div id="app">
  <Todos
    v-for="todo in todos"
    v-bind:todo="todo"
  >
  </Todos>
</div>

<script>


var app = new Vue({
  el: '#app',
  data: {
    todos: []
  },
  mounted: function () {
    fetch("/todos")
     .then(response => response.json())
     .then(todos => {
        this.todos = todos
     })
  }
})

Vue.component('Todos', {
  props: ['todo'],
  template: '<li>{{todo.title}} {{todo.status}}</li>',
})


</script>