import Vue from 'vue'
import Sample from '@/components/Sample'
import * as flushPromises from "flush-promises";


describe('sample', () => {
  beforeEach(() => {
    fetch.resetMocks()
  })

  let sampleComponent = (() => {
    fetch.mockResponseOnce([]);
    const Constructor = Vue.extend(Sample)
    return new Constructor().$mount()
  })

  it('show h1', () => {
    expect(sampleComponent().$el.querySelector('#sample h1').textContent).toEqual('Sample page!')
  })
})
