<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>

<h1>Todos List</h1>


<div id="app">
  <input name="title"  v-model="title" />
  <input type="submit" id="add_todo" v-on:click="addTodo" />
  <Todos
    v-for="todo in todos"
    v-bind:todo="todo"
  >
  </Todos>
</div>

<script src="todos.js"></script>
