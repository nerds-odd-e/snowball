<template>
  <div id="todo">
    <h1></h1>
    <input name="todo_item" v-model="inputValue" />
    <input type="button" id="todo_register" v-on:click="addTodo">
    <div v-for="todo in todos" class="todo_list">
      <input class="row" :value="todo.title">
    </div>
  </div>

</template>

<script>
import HttpClient from './HttpClient.js'
export default {
  name: 'Todo',
  mounted () {
    HttpClient.fetch('/todo')
    .then(response => {
      this.todos = response;
    })
  },
  data () {
    return {
      todos: [],
      inputValue: '',
    }
  },
  methods: {
    addTodo() {
      this.todos.push({title: this.inputValue})
      HttpClient.fetch('/todo', {method: 'POST',body:JSON.stringify({inputValue:this.inputValue})})
    }
  }
}
</script>
