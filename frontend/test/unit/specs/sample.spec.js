import Vue from 'vue'
import Sample from '@/components/Sample'

const nextTick = () => new Promise(res => process.nextTick(res));

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

  it('fetch response', async () => {
    fetch.mockResponseOnce(JSON.stringify({msg:'hello!'}));
    const Constructor = Vue.extend(Sample)
    const vm = new Constructor().$mount()

    await nextTick()

    expect(fetch.mock.calls.length).toEqual(1)
    expect(fetch.mock.calls[0][0]).toEqual('/sample')
    expect(vm.message).toEqual('hello!')
    expect(vm.$el.querySelector("#sample h2").textContent).toEqual('hello!')
  })
})
