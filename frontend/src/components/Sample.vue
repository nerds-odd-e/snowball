<template>
  <div id="sample">
    <h1>Sample page!</h1>
    <h2>{{message}}</h2>
  </div>
</template>

<script>
export default {
  name: 'Sample',
  mounted () {
    fetch('/sample')
      .then(response => {
        return response.json()
      })
      .then(json => {
        this.message = json.msg
      })
  },
  data () {
    return {
      message: ''
    }
  },
  methods: {
  }
}
</script>
