<template>
  <div id="todos">
    <h1>Todos List</h1>
    <input name="title"  v-model="title" />
    <input type="submit" id="add_todo" v-on:click="addTodo" />
    <input id="search" name="search" v-model="searchWord" />
    <li class='todo' v-for="todo in searchedTodos">{{todo.title}} {{todo.status}}</li>
  </div>
</template>

<script>
export default {
  name: 'Todos',
  mounted() {
    let options = {mode: 'cors'}
    fetch("/todos", options)
     .then(response => response.json())
     .then(todos => {
        this.todos = todos
     })
  },
  data () {
    return {
      searchWord: '',
      title: '',
      todos: [],
    }
  },
  computed: {
    searchedTodos() {
      return this.todos.filter((o)=>o.title.includes(this.searchWord))
    }
  },
  methods: {
    addTodo() {
      let params = {'title': this.title}
      let options = {
           'method': 'POST',
           'body': JSON.stringify(params)
      }
      fetch('/addTodo', options)
    }
  }
}
</script>

