<template>
  <div id="todo">
    <h1></h1>
    <input name="todo_item" v-model="inputValue" />
    <input type="button" id="todo_register" v-on:click="addTodo">
    <div v-for="todo in todos" class="todo_list">
      <input :value="todo.title">
    </div>
  </div>

</template>

<script>
export default {
  name: 'Todo',
  mounted () {
    fetch('http://localhost:8060/todo').
    then(response =>
    response.json()
    ).
    then(response => {
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
      fetch('http://localhost:8060/todo', {method: 'POST',body:JSON.stringify({inputValue:this.inputValue})})
    }
  }
}
</script>
