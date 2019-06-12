<template>
  <div id="todos">
    <h1>Todos List</h1>
    <input name="title"  v-model="title" />
    <input type="submit" id="add_todo" v-on:click="addTodo" />
    <li v-for="todo in todos">{{todo.title}} {{todo.status}}</li>
  </div>
</template>

<script>
export default {
  name: 'Todos',
  mounted() {
    let options = {mode: 'cors'}
    fetch("http://localhost:8070/todos", options)
     .then(response => response.json())
     .then(todos => {
        this.todos = todos
     })
  },
  data () {
    return {
      title: '',
      todos: [],
      msg: 'Welcome to Your Vue.js App'
    }
  },
  methods: {
    addTodo() {
      let params = {'title': this.title}
      let options = {
           'method': 'POST',
           'body': JSON.stringify(params)
      }
      fetch('http://localhost:8070/addTodo', options)
    }
  }
}
</script>

